package com.Voxloud.AndriiHubarenko.services.interfaces;

import com.Voxloud.AndriiHubarenko.domain.Tag;

import java.util.Collection;
import java.util.Set;

public interface TagServiceInterface {

    Set<Tag> findOrCreate(Collection<Tag> tags);
}
