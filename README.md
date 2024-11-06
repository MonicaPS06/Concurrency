Team Members:
(1) Arthisree Saraswathi Rajamanickam - 7216696
(2) Monica Ponnappadhas Santhini - 7221964
(3) Daranya Maniseharan - 7222079

This project is designed to simulate an energy management system, featuring concurrent charging and usage of batteries by multiple energy sources and objects.
It demonstrates multithreaded charging and usage control, along with safe handling of overloads, ensuring data consistency and efficient resource utilization.
The implementation includes both blocking and non-blocking concurrency algorithms to manage and simulate concurrent charging and usage of batteries.

Functionality Overview
Multithreaded Charging Simulation:

Multiple energy sources (e.g., Solar, Wind, and Hydro) simultaneously charge a reserved battery.
Synchronization is implemented to avoid race conditions, ensuring accurate battery charging levels.
Overcharging beyond battery capacity is prevented using a monitoring mechanism.
Multithreaded Usage Simulation:

Simultaneous usage of battery resources by various energy-consuming objects.
Overload control mechanisms are in place to prevent excessive energy draw, ensuring system stability.
Team Responsibilities and Deliverables

Monica: Battery Charging Simulation
Implementations:
Code to simulate multithreaded battery charging from multiple energy sources.
Includes synchronization mechanisms to avoid overcharging and handle concurrency effectively.
Deliverables:
EnergySourceSimulator class with methods to handle concurrent charging.

Arthisree: Battery Usage Simulation and Overload Control
Implementations:
Code to simulate concurrent battery usage by various energy objects.
Mechanisms to monitor and control battery usage, preventing overload scenarios.
Deliverables:
BatteryUsageSimulator class with overload monitoring.

Daranya: Concurrency Model Comparison 
Implementations:
Research and provide answers to concurrency-related questions (comparison of concurrency models, concurrency vs. parallelism, blocking vs. non-blocking concurrency algorithms).
Deliverables:
explanations of concurrency models in the README.md.

The Answers were given in a separate file
