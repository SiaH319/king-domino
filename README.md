# Kingdomino

Check out our [wiki page](https://github.com/McGill-ECSE223-Winter2020/ecse223-group-project-04/wiki)!

## Project Overview

Create the Kingdomino application for the well-known board game Kingdomino.
Players are identified by their color (green, blue, yellow, pink).
There are six types of terrains, namely, wheat field (W: yellow), lake (L, blue),
forest (F: dark green), grass (G: light green), mountain (M: black), swamp (S: light brown).
Dominos are uniquely identified by their number (between 1 and 48) and their actual content
(i.e. type of terrain on each tile and the number of crowns).
The figure on the right presents all the dominos in an increasing order (i.e. top-left is 1, bottom-right is 48).
For test files, these dominos will be represented textually.
For example, 19:F+W(1) denotes domino #19 containing a wheat field with one crown and a
forest without a crown (thus the default value of crowns is 0 if not indicated otherwise). This project is a part of ECSE223 course.

### Team Members

| Name          | Major         | Year  |
| ------------- |:-------------:| -----:|
| Violet Wei    | Software Eng. |  U2   |
| Sia Ham       | Software Eng. |  U1   |
| Mohmad Dimassi| Computer Eng. |  U1   |
| Cecilia Jiang | Software Eng. |  U2   |
| Yuta Youness Bellali | Software Eng.  |  U1   |
| Ezer Berdugo | Computer Eng. |  U2   |

## Deliverable 1

Deliverables for Iteration 1 – Domain Model, Constraints, GUI Mockup [wiki](https://github.com/McGill-ECSE223-Winter2020/ecse223-group-project-04/wiki/Iteration-1)
- Use Umple to define the domain model showing all concepts and relationships of the
Kingdomino game including all features to be developed to play the game.
- Capture the 10 most important (interesting) constraints of the domain which are not covered by
your domain model in a controlled natural language.
- For each feature of the game, you will need to create a mockup of the user interface and
document it in the wiki of your Github repository.
- Generate code from the domain model and commit the code to your group’s repository in the
GitHub organization of the course (https://github.com/McGill-ECSE223-Winter2020/).

The domain model of our Kingdomino application can be found [here](https://github.com/McGill-ECSE223-Winter2020/ecse223-group-project-04/wiki/Domain-Model)

## Deliverable 2

*Interation 2: Specification of Controller interfaces, Implementation of Controller methods, Mapping
of Gherkin scenarios*

- As an input for this deliverable, you will receive user stories and scenarios written in the Gherkin
language, which capture the main interactions during the game.

- Assign the development of each feature to one of your team members, i.e., each team member
is individually responsible for four specific features.

- For each feature individually specify the Controller interface as needed to realize the feature.

- Implement the assigned features individually and as a team in Java as described in Iteration 2 and
commit the code to your group’s repository in the course’s GitHub organization.

- For each feature, individually map the corresponding Gherkin scenarios to acceptance tests to be
executed by Cucumber in the context of your Controller interface. All related acceptance tests
should successfully execute upon completing this deliverable.

Task Distribution is [here](https://drive.google.com/file/d/1FD2QnrGVEYC0vFBRpd7bsMtF4bkYY99A/view?fbclid=IwAR3HEj7ky-GXBVtZjpR7rJ2VKfPiTyUR-qVcHd9w07a81Plvxs6fXHJQSwI)

## Deliverable 3
Deliverables for Iteration 3 – State Machines 

In this iteration, we provide Gherkin specification of the key phases of the gameplay as an input.

- As a team, use Umple to define the state machine to control the flow of the Kingdomino game, i.e. to initialize the game, to proceed to next turn, to proceed to next player, to proceed to next phase (choosing next domino, placing domino to kingdom), and by evaluating score. 
Moreover, you are also allowed to use state machines to capture the behavior of other domain classes.

- Generate code from the state machine(s) and commit the code to your group’s repository in the
course’s GitHub organization.

- Implement the Controller part of the assigned methods of the state machine (related to events,
actions, guards) as a team in Java and commit the code to your group’s repository in the course’s
GitHub organization

- As a team, map the corresponding Gherkin scenarios to acceptance tests to be executed by
Cucumber in the context of your statemachine and the underlying domain model. 

Iteration 3 [wiki report](https://github.com/McGill-ECSE223-Winter2020/ecse223-group-project-04/wiki/Iteration-3-Report)

## Deliverable 4
Iteration 4 – Implementation

The deliverable for Iteration 4 is to complete the implementation of Kingdomino application with a
graphical user interface (GUI) following the Model-View-Controller pattern. 

Goal: Implementation of the Graphical User Interface of Kingdomino

Documentation of Bonus Features on Project [Wiki[()
