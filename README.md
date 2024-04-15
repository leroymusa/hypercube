# Hypercube

## What is a Hypercube?

A hypercube, also known as an n-dimensional cube or an n-cube, is a geometric shape that generalizes the concept of a square (2-dimensional cube) and a cube (3-dimensional cube) to higher dimensions. It represents a set of points in n-dimensional space, where each point has n coordinates.

![Hypercube GIF](https://media1.tenor.com/images/51d686669b5d9a801714366cd61807be/tenor.gif?itemid=9052304)


## How does it work?

The `Hypercube` class generates and prints hypercube corners of a given dimension using both recursive and iterative approaches. It represents hypercube corners as binary strings of coordinates (0s and 1s) and provides methods to manipulate and traverse the hypercube.

## What does the project do?

The `Hypercube.java` project implements algorithms to generate and print hypercube corners using recursive and iterative approaches. It offers clear solutions for efficiently handling hypercube corners in multiple dimensions. The implementation includes nested classes for representing hypercube corners and handling dimension validation exceptions.

## Usage

To use the `Hypercube` class, follow these steps:

1. Compile the `Hypercube.java` file.
2. Run the program and enter the dimension of the hypercube (a positive integer).
3. The program will generate and print hypercube corners using both recursive and iterative approaches for the specified dimension.

## Example

```java
public class Main {
    public static void main(String[] args) {
        Hypercube hypercube = new Hypercube();
        hypercube.main(args);
    }
}
