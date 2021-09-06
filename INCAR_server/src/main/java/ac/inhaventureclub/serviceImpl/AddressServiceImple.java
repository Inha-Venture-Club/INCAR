package ac.inhaventureclub.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ac.inhaventureclub.repository.AddressRepository;
import ac.inhaventureclub.service.AddressService;
import ac.inhaventureclub.vo.Address;

@Service("addressservice")
public class AddressServiceImple implements AddressService{
	@Autowired
	AddressRepository addressRepository;
	
	/* find */
	@Override
	public String findAll() {
		return new Gson().toJson(addressRepository.findAll());
	}
	
	@Override
	public String findAddressesWithRegion(String region) {
		return new Gson().toJson(addressRepository.findAddressesWithRegion(region));
	}
	
	@Override
	public String findAddressWithAddressindex(int addressindex) {
		return new Gson().toJson(addressRepository.findAddressWithAddressindex(addressindex));
	}
	
	/* save */
	@Override
	public int saveAddress(Address address) {
		Address resultAddress = addressRepository.saveAndFlush(address);
		// if SUCCESS = Address.class; else FAIL = null;
		return resultAddress == null ? 0:1;
	}
	
}
