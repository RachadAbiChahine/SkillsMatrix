package com.decathlon.matrix_skills;

import org.hamcrest.core.IsNot;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.http.MediaType;
import org.springframework.security.access.method.P;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
public class PostTest {


    private static final MediaType[] SUPPORTED_MEDIA_TYPES = new MediaType[]{
            MediaType.APPLICATION_XML};

    @TestFactory
    Collection<DynamicTest> testAcceptedMediaTypes() throws Exception {

        // Creating a classpath scanner to find all controller classes in project
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(new DefaultListableBeanFactory(),
                false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        Set<BeanDefinition> beanDefinitons = scanner.findCandidateComponents("com.decathlon");
        Set<Object> controllers = new HashSet<>();
        Set<Class<?>> controllersClasses = new HashSet<>();

        // Instantiating controller classes
        for (BeanDefinition beanDefiniton : beanDefinitons) {
            String className = beanDefiniton.getBeanClassName();
            Class<?> controllerClazz = ClassLoader.getSystemClassLoader().loadClass(className);
            controllersClasses.add(controllerClazz);

            Constructor<?> constructor = controllerClazz.getDeclaredConstructors()[0];
            Object[] arguments = new Object[constructor.getParameterTypes().length];
            int i = 0;
            for (Class<?> parameterType : constructor.getParameterTypes()) {
                arguments[i] = Mockito.mock(parameterType);
                i++;
            }
            controllers.add(constructor.newInstance(arguments));
        }

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controllers.toArray()).build();
        List<DynamicTest> generatedTests = new ArrayList<>();

        // Check if controller has a POST endpoint and call it with all the different
        // mediatypes, throw an error in a 415 (unsupported media type) is returned
        for (Class<?> controllerClazz : controllersClasses) {
            RequestMapping mapping = controllerClazz.getAnnotationsByType(RequestMapping.class)[0];
            StringBuilder builder = new StringBuilder();
            builder.append(mapping.value()[0]);
            for (Method method : controllerClazz.getMethods()) {
                if (method.isAnnotationPresent(PostMapping.class)) {
                    String postMapping =method.getDeclaredAnnotationsByType(PostMapping.class)[0].value()[0];
                    StringBuilder requestmap= new StringBuilder();
                    requestmap.append(postMapping).deleteCharAt(1).deleteCharAt(requestmap.toString().length()-1);
                    for (MediaType mediaType : SUPPORTED_MEDIA_TYPES) {
                        generatedTests.add(DynamicTest.dynamicTest(builder.toString()+requestmap.toString() + " " + mediaType,
                                () -> mockMvc.perform(MockMvcRequestBuilders.post(builder.toString()+requestmap.toString()).accept(mediaType).contentType(mediaType))
                                        .andExpect(status().is(IsNot.not(415))).andExpect(status().is(IsNot.not(404)))));

                    }
                }
            }

        }

        return generatedTests;
    }
}


