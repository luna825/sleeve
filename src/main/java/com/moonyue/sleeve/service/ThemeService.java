package com.moonyue.sleeve.service;

import com.moonyue.sleeve.model.Theme;
import com.moonyue.sleeve.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    public List<Theme> getByNames(List<String> names){
        return this.themeRepository.findByNames(names);
    }

    public Optional<Theme> getByName(String name){
        return this.themeRepository.findOneByName(name);
    }
}
