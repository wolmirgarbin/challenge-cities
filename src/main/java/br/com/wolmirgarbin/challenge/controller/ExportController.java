package br.com.wolmirgarbin.challenge.controller;

import br.com.wolmirgarbin.challenge.exception.ExportException;
import br.com.wolmirgarbin.challenge.exports.ExportFactory;
import br.com.wolmirgarbin.challenge.exports.ExportType;
import br.com.wolmirgarbin.challenge.model.Place;
import br.com.wolmirgarbin.challenge.service.PlaceService;
import com.google.common.net.HttpHeaders;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("export")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ExportController {

    PlaceService placeService;

    @ApiOperation(value = "Permite exportar os dados para JSON ou CSV")
    @GetMapping("{type}")
    public void exportToFormat(@PathVariable("type") String type, HttpServletResponse response) throws ExportException {
        ExportType exportType = ExportType.getValueOrThrow(type.toUpperCase());

        List<Place> placeList = placeService.findAllCities().stream()
                .map(Place::adapter)
                .sorted(Comparator.comparing(Place::getNomeCidade))
                .collect(Collectors.toUnmodifiableList());

        response.setCharacterEncoding("UTF-8");
        response.setContentType(exportType.getContentType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"file." + exportType.name().toLowerCase() + "\"");
        try {
            ExportFactory.get(exportType).export(new LinkedList<>(placeList), response.getWriter());
        } catch (IOException e) {
            LOGGER.error("Error exporting data.", e);
            throw new ExportException(e.getMessage());
        }
    }
}
