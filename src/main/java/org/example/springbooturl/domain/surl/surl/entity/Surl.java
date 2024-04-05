package org.example.springbooturl.domain.surl.surl.entity;

import jakarta.persistence.Entity;
import org.example.springbooturl.global.jpa.entity.BaseTime;

@Entity
public class Surl extends BaseTime {
    private String url;
    private String title;
}
