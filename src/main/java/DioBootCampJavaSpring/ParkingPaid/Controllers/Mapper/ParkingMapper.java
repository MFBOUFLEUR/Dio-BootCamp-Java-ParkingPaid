package DioBootCampJavaSpring.ParkingPaid.Controllers.Mapper;


import DioBootCampJavaSpring.ParkingPaid.Controllers.Dto.ParkingCreateDto;
import DioBootCampJavaSpring.ParkingPaid.Controllers.Dto.ParkingDTO;
import DioBootCampJavaSpring.ParkingPaid.Model.Parking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//Responsável por fazer conversão;
@Component
public class ParkingMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public ParkingDTO toParkingDTO(Parking parking) {
        return MODEL_MAPPER.map(parking, ParkingDTO.class);
    }
    public List<ParkingDTO> toParkingDTOList(List<Parking> parkingList){
        return parkingList.stream().map(this::toParkingDTO).collect(Collectors.toList());
    }

    public Parking toParking(ParkingDTO dto) {
        return MODEL_MAPPER.map(dto, Parking.class);
    }

    public Parking toParkingCreate(ParkingCreateDto dto) {
        return MODEL_MAPPER.map(dto, Parking.class);
    }
}
