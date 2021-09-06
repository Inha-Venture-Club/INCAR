package ac.inhaventureclub.service;

import org.springframework.stereotype.Service;

import ac.inhaventureclub.vo.Address;

@Service
public interface AddressService {
	/* find */
	public String findAll();
	public String findAddressesWithRegion(String region);
	public String findAddressWithAddressindex(int addressindex);
	
	/* save */ 
	public int saveAddress(Address address);
	
}
