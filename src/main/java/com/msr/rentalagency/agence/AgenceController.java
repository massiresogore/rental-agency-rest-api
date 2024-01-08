package com.msr.rentalagency.agence;

import com.msr.rentalagency.agence.converter.AgenceDtoToAgenceConverter;
import com.msr.rentalagency.agence.converter.AgenceToAgenceDtoConverter;
import com.msr.rentalagency.agence.dto.AgenceDto;
import com.msr.rentalagency.system.Result;
import com.msr.rentalagency.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/agences")
public class AgenceController {

    public final AgenceService agenceService;
    public final AgenceToAgenceDtoConverter agenceToAgenceDtoConverter;
    public final AgenceDtoToAgenceConverter agenceDtoToAgenceConverter;

    public AgenceController(AgenceService agenceService, AgenceToAgenceDtoConverter agenceToAgenceDtoConverter, AgenceDtoToAgenceConverter agenceDtoToAgenceConverter) {
        this.agenceService = agenceService;
        this.agenceToAgenceDtoConverter = agenceToAgenceDtoConverter;
        this.agenceDtoToAgenceConverter = agenceDtoToAgenceConverter;
    }

    @GetMapping
    public Result getAllAgence()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Find all success",
                this.agenceService.getAllAgence().stream().map(agenceToAgenceDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @GetMapping("/{agenceId}")
    public Result getAgneceById(@PathVariable("agenceId") Integer agenceId)
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Find one success",
                this.agenceToAgenceDtoConverter.convert(this.agenceService.findById(agenceId))

        );
    }

    @DeleteMapping("/{agenceId}")
    public Result deleteAgence(@PathVariable("agenceId") Integer agenceId)
    {
        this.agenceService.delete(agenceId);
        return  new Result(true,StatusCode.SUCCESS,"Detele agence success");
    }

    @PostMapping
    public Result addAgence(@Valid @RequestBody AgenceDto agenceDto)
    {
        //convert to agence
       Agence agence = agenceDtoToAgenceConverter.convert(agenceDto);

       Agence savedAgence = this.agenceService.save(agence);


        //convert to AgenceDto and  send response
        return  new Result(true,StatusCode.SUCCESS,
                "Create agence success",
                agenceToAgenceDtoConverter.convert(savedAgence));
    }

    @PutMapping("/{agenceId}")
    public Result updateAgence(@Valid @RequestBody AgenceDto agenceDto, @PathVariable Integer agenceId)
    {
        return  new Result(true,StatusCode.SUCCESS,
                "Update success",
                this.agenceToAgenceDtoConverter.convert(this.agenceService.update(agenceId,this.agenceDtoToAgenceConverter.convert(agenceDto))));
    }

}
