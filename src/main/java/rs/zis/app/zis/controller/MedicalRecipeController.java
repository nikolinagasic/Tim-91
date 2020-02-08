package rs.zis.app.zis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.zis.app.zis.config.WebConfig;
import rs.zis.app.zis.domain.MedicalRecipe;
import rs.zis.app.zis.dto.MedicalRecipeDTO;
import rs.zis.app.zis.service.MedicalRecipeService;
import rs.zis.app.zis.service.NurseService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@RestController
@RequestMapping("/medicalrecipe")
public class MedicalRecipeController extends WebConfig {

    @Autowired
    MedicalRecipeService medicalRecipeService;

    @Autowired
    NurseService nurseService;

    @PostMapping(consumes = "application/json" , value = "/save_recipe")
    public ResponseEntity<Integer> saveRecipe(@RequestBody MedicalRecipeDTO medicalRecipeDTO){
        MedicalRecipe medicalRecipe = medicalRecipeService.save(medicalRecipeDTO);
        if(medicalRecipe==null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(0, HttpStatus.CREATED);
    }

    @PostMapping(consumes = "application/json" , value = "/overi")
    public ResponseEntity<Integer> overiRecept(@RequestBody MedicalRecipeDTO medicalRecipeDTO){
        MedicalRecipe medicalRecipe = medicalRecipeService.overi(medicalRecipeDTO);
        if(medicalRecipe==null){
            return new ResponseEntity<>(-2, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(0, HttpStatus.CREATED);
    }

    @GetMapping(value= "/getRecipes")
    public ResponseEntity<?> getRecipes(){
        List<MedicalRecipe>medicalRecipeList = medicalRecipeService.findAll();
        List<MedicalRecipeDTO>medicalRecipeDTOS = new ArrayList<>();           //lista neovernih recepata
        for(MedicalRecipe medicalRecipe: medicalRecipeList){
            if(medicalRecipe.isOveren()==false){
                MedicalRecipeDTO medicalRecipeDTO = new MedicalRecipeDTO();
                medicalRecipeDTO.setId(medicalRecipe.getId());
                medicalRecipeDTO.setSifraDijagnoze(medicalRecipe.getSifraDijagnoza());
                medicalRecipeDTO.setSifraLeka(medicalRecipe.getSifraLeka());
                medicalRecipeDTO.setNazivLeka(medicalRecipe.getNazivLeka());
                medicalRecipeDTO.setNacinKoriscenja(medicalRecipe.getNacinKoriscenja());
                medicalRecipeDTO.setOveren(medicalRecipe.isOveren());
                medicalRecipeDTO.setDoctor_name(medicalRecipe.getDoctor_name());
                medicalRecipeDTOS.add(medicalRecipeDTO);
            }
        }
        return new ResponseEntity<>(medicalRecipeDTOS,HttpStatus.OK);
    }



}
