package com.bot.weather.vk.core.weather.svgPictures;

import com.bot.weather.vk.core.FilePath;
import com.bot.weather.vk.core.weather.dto.WeatherInfoDto;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.springframework.stereotype.Component;
import org.w3c.dom.svg.SVGDocument;

import java.io.*;
import java.util.Objects;

@Component
public class WeatherPictureConverter {

    public void createPngPicture(WeatherInfoDto weatherInfoDto) throws IOException {
        OutputStream pngStream = null;

        try {
            pngStream = new FileOutputStream(FilePath.weatherCascade);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        TranscoderOutput outputPngImage = new TranscoderOutput(pngStream);
        PNGTranscoder myConverter = new PNGTranscoder();
        try {
            myConverter.transcode(new TranscoderInput(getSvg(weatherInfoDto)), outputPngImage);
        } catch (TranscoderException e) {
            e.printStackTrace();
        } finally {
            try {
                assert pngStream != null;
                pngStream.flush();
                pngStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private SVGDocument getSvg(WeatherInfoDto weatherInfoDto) throws IOException {
        String svgText = replaceAndGetSvgText(weatherInfoDto);
        SAXSVGDocumentFactory factory;
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        factory = new SAXSVGDocumentFactory(parser);
        return factory.createSVGDocument(null, new StringReader(svgText));
    }

    private String replaceAndGetSvgText(WeatherInfoDto weatherInfoDto) throws IOException {
        String replacement = "%s";
        String svgText = getSvgCascade(weatherInfoDto.getWeatherCondition());
        String replacedSvgText = svgText
                .replace("Temp", replacement)
                .replace("City", replacement)
                .replace("Country", replacement)
                .replace("Description", replacement)
                .replace("Humidity", "Влажность:")
                .replace("Atmosphere pressure", "Атм давл:")
                .replace("Wind", "Ветер:");
        return String.format(replacedSvgText,
                weatherInfoDto.getTemperature(),
                weatherInfoDto.getCity(),
                weatherInfoDto.getCountry(),
                weatherInfoDto.getDescription(),
                weatherInfoDto.getHumidity(),
                weatherInfoDto.getPressure(),
                weatherInfoDto.getWind());
    }

    private String getSvgCascade(String weatherCondition) throws IOException {
        String trimmedWeatherCondition = weatherCondition.substring(0, 2);
        String svgFileName = switch (trimmedWeatherCondition) {
            case "01" -> "Cascade1.svg";
            case "02", "03", "04" -> "Cascade2-4.svg";
            case "09", "10" -> "Cascade9-10.svg";
            case "11" -> "Cascade11.svg";
            case "13" -> "Cascade13.svg";
            case "50" -> "Cascade50.svg";
            default -> throw new IllegalArgumentException("Unidentified weather condition: " + weatherCondition);
        };
        try (InputStream inputStream = getClass().getResourceAsStream("/weatherCascades/" + svgFileName)) {
            return new String(Objects.requireNonNull(inputStream).readAllBytes());
        }
    }
}