package rs.zis.app.zis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.TermDefinition;
import rs.zis.app.zis.dto.TermDefinitionDTO;
import rs.zis.app.zis.service.TermDefinitionService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RestController
@RequestMapping("/term_definition")
public class TermDefinitionController extends WebConfig {

    @Autowired
    private TermDefinitionService termDefinitionService;

    @GetMapping(produces = "application/json", value = "/getAll")
    public ResponseEntity<?> getAllTermDefinitions() {
        List<TermDefinitionDTO> termDefinitionDTOS = new ArrayList<>();
        for (TermDefinition termDefinition : termDefinitionService.findAll()) {
            termDefinitionDTOS.add(new TermDefinitionDTO(termDefinition));
        }

        return new ResponseEntity<>(termDefinitionDTOS, HttpStatus.OK);
    }

}
