# Sachem Aftershock Swerve Library

This repository is a fork of the unmaintained SDS Swerve Library, updated to interface the Phoenix Pro Library and include methods needed for updated WPILib calls.

## Basic Structure

Each Swerve Module contains: 
- Drive Controller
    - Controls Driving Motor
- Steer Controller
    - Uses PID to control Steer Motor to given angle. Uses Absolute Encoder (CANCoder) to set the starting position of the relative encoder within the motor. This `relative = absolute` call occurs multiple times on startup as the CANCoder can take longer to boot than the motors.

The implementation for each of these exists within the `ctre` and `rev` folders, in the `Falcon500DriveControllerFactory`, `Falcon500SteerControllerFactory`,`NeoDriveControllerFactory`, and `NeoSteerControllerFactory` files.

## Usage

When using this library, use the `ModuleHelper` file for your given Swerve Module set. For the `Swerve2023` project, we used the `Mk4SwerveModuleHelper` file for our Mk4 modules.

Within each of the `ModuleHelper` files, are multiple builders for your configuration of motors. For our configuration, a Falcon500 drive motor and Neo steer motor, we would use the `createFalcon500Neo` call.


