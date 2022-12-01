package exam.service.impl;

import exam.model.dto.Shops.ShopImportDto;
import exam.model.dto.Shops.ShopWrapperDto;
import exam.model.entity.Shop;
import exam.model.entity.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static exam.constants.Messages.INVALID_SHOP_MSG;
import static exam.constants.Messages.SUCCESSFUL_ADDED_SHOP_MSG;
import static exam.constants.Paths.GET_SHOPS_FROM_FILE;

@Service
@Getter
@Setter

public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count()>0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(GET_SHOPS_FROM_FILE.toPath());
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        ShopWrapperDto shopWrapperList = this.xmlParser.fromFile(GET_SHOPS_FROM_FILE.toPath(), ShopWrapperDto.class);
        List<ShopImportDto> shopImportList =  shopWrapperList.getShopWrapperList();
        shopImportList.forEach(current->{
            boolean isValid = this.validationUtils.isValid(current);

            if (this.shopRepository.findFirstByName(current.getName()).isPresent()){
                isValid = false;
            }

            if (isValid){
                sb.append(String.format(SUCCESSFUL_ADDED_SHOP_MSG,current.getName(),current.getIncome())).append(System.lineSeparator());
                Shop shop = this.modelMapper.map(current, Shop.class);
                Town town = this.townRepository.findFirstByName(current.getTowns().getTownName()).get();
                shop.setTowns(town);

                this.shopRepository.saveAndFlush(shop);
            }else {

                sb.append(INVALID_SHOP_MSG).append(System.lineSeparator());
            }

        });
        return sb.toString();
    }
}
