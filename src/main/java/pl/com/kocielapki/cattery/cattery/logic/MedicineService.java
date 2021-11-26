package pl.com.kocielapki.cattery.cattery.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.kocielapki.cattery.cattery.data.Medicine;
import pl.com.kocielapki.cattery.cattery.repo.MedicineRepository;

@Service
@Transactional
public class MedicineService {
   private MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Medicine get(Long id){
        return medicineRepository.findById(id).get();
    }

}
