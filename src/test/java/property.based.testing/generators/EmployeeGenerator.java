package property.based.testing.generators;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import property.based.testing.Employee;

public class EmployeeGenerator extends Generator<Employee> {

    protected EmployeeGenerator() {
        super(Employee.class);
    }

    @Override
    public Employee generate(SourceOfRandomness sourceOfRandomness, GenerationStatus generationStatus) {
        return null;
    }


}
