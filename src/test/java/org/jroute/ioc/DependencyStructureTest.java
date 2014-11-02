package org.jroute.ioc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.internal.util.collections.Sets.newSet;

public class DependencyStructureTest {


    @Rule
    public ExpectedException expected = ExpectedException.none();


    @Test
    public void shouldCreateEmptyInstancesForEmptySet() {
        // given
        DependencyStructure structure = new DependencyStructure(new HashSet<Class<?>>());

        //when
        List<Set<Class<?>>> groups = structure.create();

        // then
        assertTrue(groups.isEmpty());
    }


    public static class A {

    }

    @Test
    public void shouldCreateStructureForOneClass() throws Exception {
        // given
        DependencyStructure structure = new DependencyStructure(newSet(A.class));

        //when
        List<Set<Class<?>>> groups = structure.create();

        // then
        assertThat(groups.size(), is(1));
        assertTrue(groups.get(0).contains(A.class));
    }

    public static class B {

        public B(A a) {
        }
    }

    @Test
    public void shouldCreateStructureWithDependency() throws Exception {
        // given
        DependencyStructure structure = new DependencyStructure(newSet(A.class, B.class));

        //when
        List<Set<Class<?>>> groups = structure.create();

        // then
        assertThat(groups.size(), is(2));
        assertTrue(groups.get(0).contains(A.class));
        assertTrue(groups.get(1).contains(B.class));
    }


    public static class C {

        public C(D d) {
        }
    }

    public static class D {

        public D(E e) {
        }
    }

    public static class E {

        public E(C c) {
        }
    }

    @Test
    public void shouldDetectCircularDependencies() throws Exception {
        try {
            // given
            DependencyStructure structure =
                    new DependencyStructure(newSet(C.class, E.class, D.class));

            // when
            structure.create();
        } finally {
            // then
            expected.expect(IllegalStateException.class);
            expected.expectMessage("Found circular dependency!");
        }

    }

    public static class F {

    }

    public static class G {

        public G(A a, F b) {
        }
    }

    public static class H {

        public H(G g, B b) {

        }
    }

    public static class I {

        public I(B b) {

        }
    }

    @Test
    public void shouldCreateComplexStructure() throws Exception {
        // given
        DependencyStructure structure =
                new DependencyStructure(newSet(A.class, G.class, B.class, I.class, F.class, H.class));

        // when
        List<Set<Class<?>>> groups = structure.create();

        // then
        assertThat(groups.size(), is(3));
        assertThat(groups.get(0), hasItems(F.class, A.class));
        assertThat(groups.get(1), hasItems(G.class, B.class));
        assertThat(groups.get(2), hasItems(H.class, I.class));
    }

    @DependsOn({ F.class, A.class })
    public static class J {

    }

    @Test
    public void shouldUseAnnotationIfExists() throws Exception {
        // given
        DependencyStructure structure = new DependencyStructure(newSet(A.class, F.class, J.class));

        // when
        List<Set<Class<?>>> groups = structure.create();

        // then
        assertThat(groups.size(), is(2));
        assertThat(groups.get(0), hasItems(F.class, A.class));
        assertThat(groups.get(1), hasItems(J.class));
    }


    @Test
    public void shouldEnablePreAllocatedClasses() throws Exception {
        // given
        DependencyStructure structure = new DependencyStructure(newSet(B.class), newSet(A.class));

        // when
        List<Set<Class<?>>> groups = structure.create();

        // then
        assertThat(groups.size(), is(1));
        assertThat(groups.get(0), hasItems(B.class));
    }

}