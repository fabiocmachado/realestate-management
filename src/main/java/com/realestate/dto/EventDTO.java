package com.realestate.dto;

import java.time.LocalDateTime;

public class EventDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long agentId;

    public EventDTO() {
    }

    public EventDTO(Long id, String title, String description, LocalDateTime start, LocalDateTime end, Long agentId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.agentId = agentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
}
