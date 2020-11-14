package com.sebastiandagostino.kalah.service;

public interface MapperService<Model, DTO> {

    DTO mapDTO(DTO dto, Model model);

    Model mapModel(DTO dto);
}
