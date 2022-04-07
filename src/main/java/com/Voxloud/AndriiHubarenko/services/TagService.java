package com.Voxloud.AndriiHubarenko.services;

import com.Voxloud.AndriiHubarenko.domain.Tag;
import com.Voxloud.AndriiHubarenko.repositories.TagRepository;
import com.Voxloud.AndriiHubarenko.services.interfaces.TagServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService implements TagServiceInterface {

    private final TagRepository tagRepository;

    @Autowired
    public TagService (TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }
    @Override
    @Transactional
    public Set<Tag> findOrCreate(Collection<Tag> tags) {
        return tags.stream().map(t -> {

            Optional<Tag> foundTag = tagRepository.findByName(t.getName());

            if (foundTag.isPresent()) {
                return foundTag.get();
            } else {
                return tagRepository.save(t);
            }
        }).collect(Collectors.toSet());
    }
}
