package edu.netcracker.jobdealer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyFilters {
    private int limit;
    private int offset;
    private Integer money;
    private List<String> requestedSkills;
    private String vacancyName;
    private String companyName;
    private String sortBy;
    private boolean descending;
}
