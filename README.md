# Hypercube

## What is a Hypercube?

A hypercube, also known as an n-dimensional cube or an n-cube, is a geometric shape that generalizes the concept of a square (2-dimensional cube) and a cube (3-dimensional cube) to higher dimensions. It represents a set of points in n-dimensional space, where each point has n coordinates.

![Alt text]([image_path](https://ca.images.search.yahoo.com/search/images;_ylt=AwrFeAnXkR1mFlkjY_btFAx.;_ylu=c2VjA3NlYXJjaARzbGsDYnV0dG9u;_ylc=X1MDMjExNDcyMTAwNQRfcgMyBGZyA21jYWZlZQRmcjIDcDpzLHY6aSxtOnNiLXRvcARncHJpZANVTWhIdVFoUlR1ZTZqeVNYYTJsZnhBBG5fcnNsdAMwBG5fc3VnZwMwBG9yaWdpbgNjYS5pbWFnZXMuc2VhcmNoLnlhaG9vLmNvbQRwb3MDMARwcXN0cgMEcHFzdHJsAzAEcXN0cmwDMjIEcXVlcnkDM2QlMjBoeXBlcmN1YmUlMjBpbWFnZSUyMGdpZgR0X3N0bXADMTcxMzIxMzkyMQ--?p=3d+hypercube+image+gif&fr=mcafee&fr2=p%3As%2Cv%3Ai%2Cm%3Asb-top&ei=UTF-8&x=wrt&type=E211CA885G0#id=3&iurl=https%3A%2F%2Fmedia1.tenor.com%2Fimages%2F51d686669b5d9a801714366cd61807be%2Ftenor.gif%3Fitemid%3D9052304&action=click))

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
