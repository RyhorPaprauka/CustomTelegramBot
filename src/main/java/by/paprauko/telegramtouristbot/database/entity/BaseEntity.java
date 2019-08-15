package by.paprauko.telegramtouristbot.database.entity;

import java.io.Serializable;

public interface BaseEntity<PK extends Serializable> {

    PK getId();
}
