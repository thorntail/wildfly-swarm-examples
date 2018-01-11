package org.wildfly.swarm.examples.springdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wildfly.swarm.examples.springdata.EmployeeNotFound;
import org.wildfly.swarm.examples.springdata.model.Employee;
import org.wildfly.swarm.examples.springdata.repository.EmployeeRepository;

/**
 * @author Ken Finnigan
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Employee create(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    @Transactional(rollbackFor = EmployeeNotFound.class)
    public Employee delete(int id) throws EmployeeNotFound {
        Employee toBeDeleted = employeeRepository.findOne(id);

        if (toBeDeleted == null) throw new EmployeeNotFound();

        employeeRepository.delete(toBeDeleted);
        return toBeDeleted;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = EmployeeNotFound.class)
    public Employee update(Employee employee) throws EmployeeNotFound {
        Employee empToUpdate = employeeRepository.findOne(employee.getId());

        if (empToUpdate == null) throw new EmployeeNotFound();

        empToUpdate.setName(employee.getName());
        employeeRepository.saveAndFlush(empToUpdate);
        return empToUpdate;
    }

    @Override
    public Employee findById(int id) {
        return employeeRepository.findOne(id);
    }
}
