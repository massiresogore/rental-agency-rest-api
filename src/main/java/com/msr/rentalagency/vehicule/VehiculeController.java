package com.msr.rentalagency.vehicule;

import com.msr.rentalagency.system.Result;
import com.msr.rentalagency.system.StatusCode;
import com.msr.rentalagency.vehicule.converter.VehiculeDtoToVehiculeConverter;
import com.msr.rentalagency.vehicule.converter.VehiculeToVehiculeDtoConverter;
import com.msr.rentalagency.vehicule.dto.VehiculeDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;
    private final VehiculeDtoToVehiculeConverter vehiculeDtoToVehiculeConverter;
    private final VehiculeToVehiculeDtoConverter vehiculeToVehiculeDtoConverter;

    public VehiculeController(VehiculeService vehiculeService, VehiculeDtoToVehiculeConverter vehiculeDtoToVehiculeConverter, VehiculeToVehiculeDtoConverter vehiculeToVehiculeDtoConverter) {
        this.vehiculeService = vehiculeService;
        this.vehiculeDtoToVehiculeConverter = vehiculeDtoToVehiculeConverter;
        this.vehiculeToVehiculeDtoConverter = vehiculeToVehiculeDtoConverter;
    }

    @PostMapping
    public Result addVehicule(@Valid @RequestBody VehiculeDto vehiculeDto)
    {   //Covert Véhicule
        Vehicule vehicule = this.vehiculeDtoToVehiculeConverter.convert(vehiculeDto);
        //Save vehicule
        Vehicule savedVehicule=  this.vehiculeService.save(vehicule);
       return new Result(true, StatusCode.SUCCESS,"Create vehicule success",this.vehiculeToVehiculeDtoConverter.convert(savedVehicule));
    }

    @GetMapping
    public Result findAllVehicule()
    {
        List<VehiculeDto> vehiculeDtoList = this.vehiculeService.findAllVehicule().stream()
                .map(this.vehiculeToVehiculeDtoConverter::convert
                        ).toList();

        return new Result(true,StatusCode.SUCCESS,"Find all success",vehiculeDtoList);
    }

    @GetMapping("/{vehiculeId}")
    public Result findOneVehicule(@PathVariable("vehiculeId") int vehiculeId)
    {

        return new Result(true,StatusCode.SUCCESS,"Find vehicule success",
                this.vehiculeToVehiculeDtoConverter.convert(this.vehiculeService.findOneVehiculeById(vehiculeId))
                );
    }

    @DeleteMapping("/{vehiculeId}")
    public Result deleteVehicule(@PathVariable("vehiculeId") int vehiculeId)
    {
        this.vehiculeService.deleteVehicule(vehiculeId);
        return new Result(true,StatusCode.SUCCESS,"Delete vehicule success");
    }

    @PutMapping("/{veniculeId}")
    public Result updateVehicule( @PathVariable("veniculeId") int veniculeId, @Valid @RequestBody VehiculeDto vehiculeDto)
    {
        //Covert VehiculeDto to Vehicule
        Vehicule vehicule = this.vehiculeDtoToVehiculeConverter.convert(vehiculeDto);

        //Update
        Vehicule  updated =  this.vehiculeService.updateVehicule(veniculeId,vehicule);

        //Convert vehicule to vehiculeDto
        VehiculeDto vupdatedVehiculeDto = this.vehiculeToVehiculeDtoConverter.convert(updated);

     return new Result(true,StatusCode.SUCCESS,"Update vehicule success",vupdatedVehiculeDto);

    }
}
