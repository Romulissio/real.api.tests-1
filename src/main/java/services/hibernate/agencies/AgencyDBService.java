package services.hibernate.agencies;

import entity.agencies.Agency;
import io.qameta.allure.Step;

import java.util.List;

public class AgencyDBService {

    private AgencyDAO agencyDAO = new AgencyDAO();

    public Agency findAgency(Long id) {
        return agencyDAO.findById(id);
    }

    public Agency findAgencyInn(Long inn) {
        return agencyDAO.findByInn(inn);
    }

    public void saveAgency(Agency agency) {
        agencyDAO.save(agency);
    }

    public void deleteAgency(Agency agency) {
        agencyDAO.delete(agency);
    }

    public void deleteAgencyById(Long agencyId) {
        agencyDAO.deleteById(agencyId);
    }

    public void deleteAllAgency(List<Agency> agencies) {
        agencyDAO.deleteAll(agencies);
    }

    public void updateAgency(Agency agency) {
        agencyDAO.update(agency);
    }

    public List<Agency> findAll() {
        return agencyDAO.findAll();
    }

    public AgencyDBService() {
    }

    public void addCollectionsAgency(List<Agency> agencyList) {
        agencyDAO.addAgencyList(agencyList);
    }
}
