package edu.oregonstate.mist.api

import edu.oregonstate.mist.departments.core.Department
import edu.oregonstate.mist.departments.db.DepartmentMockDAO
import org.junit.Test
import static org.junit.Assert.*

class DepartmentMockDAOTest {
    @Test
    void shouldReturnEmptyList() {
        assert !DepartmentMockDAO.generate(0, null)
    }

    @Test
    void shouldGenerateManyDepartments() {
        (1..10).each {
            assertEquals(DepartmentMockDAO.generate(it, null).size(), it)
        }
    }

    @Test
    void shouldNotGenerateNegativeDepartments() {
        (-10..-1).each {
            assertEquals(DepartmentMockDAO.generate(it, null).size(), 0)
        }
    }

    @Test
    void shouldReturnDepartmentsSpecifiedInConstructor() {
        DepartmentMockDAO departmentMockDAOTest
        (1..10).each {
            departmentMockDAOTest = new DepartmentMockDAO(it)
            def departments = departmentMockDAOTest.getDepartments("abc")
            assertEquals(departments.size(), it)
            departments.each { assertEquals(it.businessCenter, "abc")}
        }
    }

    @Test
    void shouldReturnEmptyListForEmpty() {
        DepartmentMockDAO departmentMockDAOTest
        (1..10).each {
            departmentMockDAOTest = new DepartmentMockDAO(it)
            assertTrue(departmentMockDAOTest.getDepartments("empty").isEmpty())
        }
    }
}
