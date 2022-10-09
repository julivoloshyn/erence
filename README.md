# ImageDifference

Image difference app can check two pictures for difference and highlight differences on the second image. 

## Technologies

Project created with:
* Java version: 1.8
* Maven version: 3.0

## Usage 

Put your images into `src/main/resources/images` directory and change paths in Main class.

`BufferedImage input1 = ImageIO.read(new File("src/main/resources/image-1.png"));`\
`BufferedImage input2 = ImageIO.read(new File("src/main/resources/image-2.png"));`

After running open new image in resources file and see highlighted differences.

### Example

* First image:

![img.png](img.png)

* Second image:

![img_1.png](img_1.png)

* Result:

![img_2.png](img_2.png)

## Setup

To run this project, install it locally

For tests running `mvn clean test` \
For project running `mvn tomcat:start`