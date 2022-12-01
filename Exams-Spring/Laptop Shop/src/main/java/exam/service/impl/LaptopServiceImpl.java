package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.Laptops.LaptopDTO;
import exam.model.entity.Laptop;
import exam.model.entity.Shop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtils;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static exam.constants.Messages.INVALID_LAPTOP_MSG;
import static exam.constants.Messages.SUCCESSFUL_ADDED_LAPTOP_MSG;
import static exam.constants.Paths.GET_LAPTOPS_FROM_FILE;

@Service
@Getter
@Setter
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ShopRepository shopRepository;

    public LaptopServiceImpl(LaptopRepository laptopRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils, ShopRepository shopRepository) {
        this.laptopRepository = laptopRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.shopRepository = shopRepository;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count()>0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(GET_LAPTOPS_FROM_FILE));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readLaptopsFileContent(), LaptopDTO[].class))
                .forEach(current->{
                    boolean isValid = this.validationUtils.isValid(current);


                    if(this.laptopRepository.findAllByMacAddress(current.getMacAddress()).isPresent()){
                        isValid = false;
                    }
                    if (isValid) {
                        sb.append(String.format(SUCCESSFUL_ADDED_LAPTOP_MSG,current.getMacAddress(),current.getCpuSpeed(),current.getRam(),current.getStorage()))
                                .append(System.lineSeparator());
                        Laptop laptop = this.modelMapper.map(current, Laptop.class);

                        Shop shop = this.shopRepository.findFirstByName(current.getShop().getName()).get();
                        laptop.setShop(shop);


                        this.laptopRepository.saveAndFlush(laptop);
                    }else {
                        sb.append(INVALID_LAPTOP_MSG).append(System.lineSeparator());
                   }
               });

        return sb.toString();
    }

    @Override
    public String exportBestLaptops() {

        StringBuilder sb = new StringBuilder();

        List<Laptop> exportList = this.laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc();

        exportList.forEach(laptop -> {
        sb.append(String.format("Laptop - %s\n" +
                "*Cpu speed - %.2f \n" +
                "**Ram - %d\n" +
                "***Storage - %d\n" +
                "****Price - %.2f\n" +
                "#Shop name - %s \n" +
                "##Town - %s \n" +
                ". . . ", laptop.getMacAddress(),laptop.getCpuSpeed(),laptop.getRam(),laptop.getStorage()
        ,laptop.getPrice(),laptop.getShop().getName(),laptop.getShop().getTowns().getName())).append(System.lineSeparator());
    });
        return sb.toString();
    }
}
