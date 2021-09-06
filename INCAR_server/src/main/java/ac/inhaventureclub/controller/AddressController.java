package ac.inhaventureclub.controller;

import javax.annotation.Resource;

import org.hibernate.annotations.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ac.inhaventureclub.repository.AddressRepository;
import ac.inhaventureclub.service.AddressService;
import ac.inhaventureclub.util.Result;
import ac.inhaventureclub.vo.Address;

@RestController
public class AddressController {
	@Autowired
	AddressRepository addressRepository;
	
	@Resource(name="addressservice")
	AddressService addressService;
	
	/* find */
	// Find Addresses
	@ResponseBody
	@GetMapping("/address")
	public String getAddresses() {
		return addressService.findAll();
	}
	// Find Addresses: with REGION = {지역별 구;}
	@ResponseBody
	@PostMapping("/addressesWithRegion")
	public String postAddressesWithRegion(@RequestBody String vo) {
		Gson gson = new Gson();
		Address address = gson.fromJson(vo, Address.class);
		return addressService.findAddressesWithRegion(address.REGION);
	}
	// Find Addresses: with ADDRESS_INDEX ={특정 구 선택;}
	@ResponseBody
	@PostMapping("/addressesWithAddressindex")
	public String postAddressesWithAddressindex(@RequestBody String vo) {
		Gson gson = new Gson();
		Address address = gson.fromJson(vo, Address.class);
		return addressService.findAddressWithAddressindex(address.ADDRESS_INDEX);
	}
	
	
	/* save */
	@ResponseBody
	@PostMapping("/address")
	public String setAddressService(@RequestBody String vo) {
		Gson gson = new Gson();
		int result = -1;
		String region= null;
		String gu = null;
		try {
			Address address = gson.fromJson(vo, Address.class);
			System.out.println(vo);
			result = addressService.saveAddress(address);
			region = address.REGION;
			gu = address.GU;
		}catch(Exception e) {
			e.printStackTrace();
		}
		Result obj = result > 0 ? new Result(true, "REGION:"+region+", GU:"+gu) : new Result(false, "FAILED");
		return gson.toJson(obj);
	}
	

}
