package rs.ac.bg.fon.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.employee.entity.TimeOffRequest;

import java.util.List;

@Repository
public interface TimeOffRequestRepository extends JpaRepository<TimeOffRequest, Integer> {
    List<TimeOffRequest> findByRequestID(Integer requestID);
    List<TimeOffRequest> findByEmployeeID(Integer emloyeeID);

}
