# Hosting on Jitpack Instructions 

- For adding a Jitpack repository to your build follow instructions on this link https://jitpack.io/
- Make a new release on Github of your repository
- Insert repository link on Jitpack to find your latest release
- In build.gradle file change "implementation 'com.github.SachemAftershock:swerve-lib:...'" to your latest release version
- IMPORTANT you have to create a new release every time, branch-SNAPSHOT does not work

# Changes 

- WPILIB updated to 2024.1.1
- Pheonix Pro library updated to 24.1.0 (Now called Pheonix 6)
- REVLib updated to 2024.0.0

# Sachem Aftershock Swerve Library

This repository is a fork of the unmaintained SDS Swerve Library, updated to interface the Phoenix Pro Library and include methods needed for updated WPILib calls.

## Basic Structure

Each Swerve Module contains: 
- Drive Controller
    - Controls Driving Motor
- Steer Controller
    - Uses PID to control Steer Motor to given angle. Uses Absolute Encoder (CANCoder) to set the starting position of the relative encoder within the motor. This [relative = absolute](https://github.com/SachemAftershock/swerve-lib/blob/b5e91b56afd8c029160b51135716b75e73b7a244/src/main/java/com/swervedrivespecialties/swervelib/rev/NeoSteerControllerFactoryBuilder.java#L131) call occurs multiple times on startup as the CANCoder can take longer to boot than the motor encoders.

The implementation for each of these exists within the `ctre` and `rev` folders, in the [Falcon500DriveControllerFactoryBuilder](https://github.com/SachemAftershock/swerve-lib/blob/phoenixPro/src/main/java/com/swervedrivespecialties/swervelib/ctre/Falcon500DriveControllerFactoryBuilder.java), [Falcon500SteerControllerFactoryBuilder](https://github.com/SachemAftershock/swerve-lib/blob/phoenixPro/src/main/java/com/swervedrivespecialties/swervelib/ctre/Falcon500SteerControllerFactoryBuilder.java),[NeoDriveControllerFactoryBuilder](https://github.com/SachemAftershock/swerve-lib/blob/phoenixPro/src/main/java/com/swervedrivespecialties/swervelib/rev/NeoDriveControllerFactoryBuilder.java), and [NeoSteerControllerFactoryBuilder](https://github.com/SachemAftershock/swerve-lib/blob/phoenixPro/src/main/java/com/swervedrivespecialties/swervelib/rev/NeoSteerControllerFactoryBuilder.java) files.

## Usage

When using this library, use the `ModuleHelper` file for your given Swerve Module set. For the `Swerve2023` project, we used the [Mk4SwerveModuleHelper](https://github.com/SachemAftershock/swerve-lib/blob/phoenixPro/src/main/java/com/swervedrivespecialties/swervelib/Mk4SwerveModuleHelper.java) file for our Mk4 modules.

Within each of the `ModuleHelper` files, are multiple builders for your configuration of motors. For our configuration, a Falcon500 drive motor and Neo steer motor, we would use the [createFalcon500Neo](https://github.com/SachemAftershock/swerve-lib/blob/b5e91b56afd8c029160b51135716b75e73b7a244/src/main/java/com/swervedrivespecialties/swervelib/Mk4SwerveModuleHelper.java#L282) call. 

This generates a swerve module with the correct configurations for your version of the swerve module, and your provided gearing.

This generates a `SwerveModule` with 4 accessible methods:
- `getDriveVelocity()`
    - Gets speed from drive motor in meters/second
- `getSteerAngle()`
    - Gets angle of drive module in `[0, 2pi)` radians
- `getPosition()`
    - Gets position from drive motor in meters
- `set(double driveVoltage, double steerAngle)`
    - Commands drive motor with voltage of driveVoltage and commands steer motor PID to reach steerAngle


