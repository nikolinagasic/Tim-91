package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.zis.app.zis.domain.MedicalRecipe;
import rs.zis.app.zis.domain.Nurse;
import rs.zis.app.zis.dto.MedicalRecipeDTO;
import rs.zis.app.zis.repository.MedicalRecipeRepository;

import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Service
public class MedicalRecipeService {

    @Autowired
    MedicalRecipeRepository medicalRecipeRepository;

    @Autowired
    NurseService nurseService;

    public List<MedicalRecipe> findAll() {return medicalRecipeRepository.findAll();}

    public Page<MedicalRecipe> findAll(Pageable page) {return medicalRecipeRepository.findAll(page);}

    public MedicalRecipe findOneById(Long id) {return medicalRecipeRepository.findOneById(id);}

    public MedicalRecipe save(MedicalRecipe medicalRecipe) {return medicalRecipeRepository.save(medicalRecipe);}

    public MedicalRecipe save(MedicalRecipeDTO medicalRecipeDTO){
        System.out.println("USAO U SAVE");
        MedicalRecipe medicalRecipe = new MedicalRecipe();
        medicalRecipe.setId(medicalRecipeDTO.getId());
        medicalRecipe.setSifraDijagnoza(medicalRecipeDTO.getSifraDijagnoze());
        medicalRecipe.setSifraLeka(medicalRecipeDTO.getSifraLeka());
        medicalRecipe.setNazivLeka(medicalRecipeDTO.getNazivLeka());
        medicalRecipe.setNacinKoriscenja(medicalRecipeDTO.getNacinKoriscenja());
        medicalRecipe.setOveren(medicalRecipeDTO.isOveren());
        medicalRecipe.setDoctor_name(medicalRecipeDTO.getDoctor_name());
        medicalRecipe = medicalRecipeRepository.save(medicalRecipe);
        return medicalRecipe;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public MedicalRecipe overi(MedicalRecipeDTO medicalRecipeDTO){
        System.out.println("USAO U OVERU");
        Nurse nurse = nurseService.findOneById(medicalRecipeDTO.getId_nurse());
        MedicalRecipe medicalRecipe = medicalRecipeRepository.findOneById(medicalRecipeDTO.getId());
        medicalRecipe.setNurse(nurse);
        medicalRecipe.setOveren(true);
        medicalRecipe = medicalRecipeRepository.save(medicalRecipe);
        nurse.addMedicalRecipe(medicalRecipe);
        nurse = nurseService.save(nurse);
        System.out.println("Zavrsio sam overu");
        return  medicalRecipe;
    }


}
