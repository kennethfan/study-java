package io.github.kennethfan.springbatchdemo.processor;

import io.github.kennethfan.springbatchdemo.dto.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person input) throws Exception {
        final String firstName = input.firstName().toUpperCase();
        final String lastName = input.lastName().toUpperCase();

        final Person output = new Person(firstName, lastName);
        log.info("Converting ({}) into ({})", input, output);

        return output;
    }
}
