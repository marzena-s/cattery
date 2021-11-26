package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.Disease;
import pl.com.kocielapki.cattery.cattery.repo.DiseaseRepository;

@Service
@Transactional
public class DiseaseService {
   private DiseaseRepository diseaseRepository;

    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    public Disease get(Long id){
        return diseaseRepository.findById(id).get();
    }

}
