package ac.inhaventureclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ac.inhaventureclub.vo.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	/* select */
	@Query("select a from Address a")
	List<Address> findAll();
	
	@Query("select a from Address a where a.REGION=:region")
	List<Address> findAddressesWithRegion(@Param("region") String region);
	
	@Query("select a from Address a where a.ADDRESS_INDEX=:addressindex")
	Address findAddressWithAddressindex(@Param("addressindex")int addressindex);
	
	/* insert */
	// Insert or Update Address data
	Address saveAndFlush(Address address);
}
