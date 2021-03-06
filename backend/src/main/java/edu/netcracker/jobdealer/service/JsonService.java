package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.*;

public interface JsonService {
    String toJson(Object o);

    VacancyFilters parseVacancyFilters(String s);

    ResumeFilters parseResumeFilters(String s);

    ResumeDto parseResumeDto(String s);

    CompanyDto parseCompanyDto(String s);

    VacancyDto parseVacancyDto(String s);

    TaskDto parseTaskDto(String s);
}