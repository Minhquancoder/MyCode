package repository;

import Facility.Facility;

import java.util.Map;

public interface IFacilityRepository {
    void addNewFacility(Facility facility);

    Map<Facility, Integer> getAll();
    void save();

    void updateUsageCount(String serviceCode);
}
