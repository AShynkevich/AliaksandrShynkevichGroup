package com.epam.lab.mentoring;

public class ForkJoinExample extends AbstractExample {
    @Override
    public void calculate() {
        /*

            def list = [1, 2, 3, 4]

            task0
                1, 2, 3, 4 - list size 4 can be split

                task01
                    1 2 - list size 2 can be split
                        task011
                            1 - process element
                        task012
                            2 - process element
                task02
                    3 4 - list size 2 can be split
                        task021
                            3 - process element
                        task022
                            4 - process element

            Q: how to keep persistent index of elements position?

         */
        System.out.println("Calculation result => " + output);
    }
}
