package DioBootCampJavaSpring.ParkingPaid.Controllers;

import DioBootCampJavaSpring.ParkingPaid.Controllers.Dto.ParkingCreateDto;
import DioBootCampJavaSpring.ParkingPaid.Controllers.Dto.ParkingDTO;
import DioBootCampJavaSpring.ParkingPaid.Controllers.Mapper.ParkingMapper;
import DioBootCampJavaSpring.ParkingPaid.Model.Parking;
import DioBootCampJavaSpring.ParkingPaid.Service.ParkingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@Api(tags="Parking Controller")
public class ParkingController {


//        // NÃO É BOM RETORNAR UMA LISTA DE DADOS DIRETO DE UMA ENTIDADE DE BANCO DE DADOS.
//        // NUNCA SE EXPÕE OBJETO DE DOMÍNIO NA API.
//        // CRIAR UM DTO QUE IRÁ REPRESENTAR A VIEW.
//        // CRIAR CAMADA DE SERVIÇO QUE IRÁ DISPONIBILIZAR O DOMNÍNIO,
//        // E NO CONTROOLER FAZER A INVERSÃO DE DOMINIO PARA O DTO.

//        @GetMapping
//        public List<Parking> findAll(){
//            var parking = new Parking();
//            parking.setColor("BROWN");
//            parking.setLicense("FFF-5555");
//            parking.setState("RS");
//            parking.setModel("New Tucson");
//            return List.of(parking);
//        }


       // Existe duas formas de injetar dependências no Spring. Primeira injetar com o @Autowired ou via construtor.
       // O Spring nas sessões mais novas não recomendam utilizar o @Autowired. No entanto via test somente dá por @AUTOWIRED.

    private final ParkingService parkingService;

    private final ParkingMapper parkingMapper;

    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    //  Ainda está devolvendo a uma entidade então vamos construir um DATA TRANSNFER ( DTO )
        //    @GetMapping
        //    public List<Parking> findAll(){
       //        return parkingService.findAll();
       //    }
    // normalmente se faria um for each... mas tem bibliotecas que fazem isso então vamos usar o MODO MAPPER

//    @GetMapping
//    public List<ParkingDTO> findAll() {
//        List<Parking> parkingList = parkingService.findAll();
//        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
//        return result;

    //AGORA RETORNAR ENTITY E NÃO LIST.

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
        Parking parking = parkingService.findById(id);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto) {
        var parkingCreate = parkingMapper.toParkingCreate(dto);
        var parking = parkingService.create(parkingCreate);
        var result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDTO parkingCreteDTO) {
        Parking parkingUpdate = parkingMapper.toParkingCreate(parkingCreteDTO);
        Parking parking = parkingService.update(id, parkingUpdate);
        return ResponseEntity.ok(parkingMapper.toParkingDTO(parking));
    }

    @PostMapping("/{id}/exit")
    public ResponseEntity<ParkingDTO> checkOut(@PathVariable String id) {
        //TODO verificar se já não esta fechado e lançar exceção
        Parking parking = parkingService.checkOut(id);
        return ResponseEntity.ok(parkingMapper.toParkingDTO(parking));
    }

}

