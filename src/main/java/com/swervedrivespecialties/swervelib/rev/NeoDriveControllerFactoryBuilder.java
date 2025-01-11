package com.swervedrivespecialties.swervelib.rev;
import com.revrobotics.spark.SparkMax;

import static com.swervedrivespecialties.swervelib.rev.RevUtils.checkNeoError;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel.PeriodicFrame;
import com.swervedrivespecialties.swervelib.DriveController;
import com.swervedrivespecialties.swervelib.DriveControllerFactory;
import com.swervedrivespecialties.swervelib.ModuleConfiguration;

public final class NeoDriveControllerFactoryBuilder {
    private double nominalVoltage = Double.NaN;
    private double currentLimit = Double.NaN;

    public NeoDriveControllerFactoryBuilder withVoltageCompensation(double nominalVoltage) {
        this.nominalVoltage = nominalVoltage;
        return this;
    }

    public boolean hasVoltageCompensation() {
        return Double.isFinite(nominalVoltage);
    }

    public NeoDriveControllerFactoryBuilder withCurrentLimit(double currentLimit) {
        this.currentLimit = currentLimit;
        return this;
    }

    public boolean hasCurrentLimit() {
        return Double.isFinite(currentLimit);
    }

    public DriveControllerFactory<ControllerImplementation, Integer> build() {
        return new FactoryImplementation();
    }

    private class FactoryImplementation implements DriveControllerFactory<ControllerImplementation, Integer> {
        @Override
        public ControllerImplementation create(Integer id, ModuleConfiguration moduleConfiguration) {

            System.out.println("----------------------- THIS IS A TEST ----------------------------------");


            SparkMax motor = new SparkMax(id, SparkLowLevel.MotorType.kBrushless);
            motor.setInverted(moduleConfiguration.isDriveInverted());

            // Setup voltage compensation
            if (hasVoltageCompensation()) {
                // checkNeoError(motor.enableVoltageCompensation(nominalVoltage), "Failed to enable voltage compensation"); TODO: See if we really need this
                
            }

            if (hasCurrentLimit()) {
                // checkNeoError(motor.setSmartCurrentLimit((int) currentLimit), "Failed to set current limit for NEO");
            }

            // checkNeoError(motor.setPeriodicFramePeriod(SparkLowLevel.PeriodicFrame.kStatus0, 100), "Failed to set periodic status frame 0 rate");
            // checkNeoError(motor.setPeriodicFramePeriod(SparkLowLevel.PeriodicFrame.kStatus1, 20), "Failed to set periodic status frame 1 rate");
            // checkNeoError(motor.setPeriodicFramePeriod(SparkLowLevel.PeriodicFrame.kStatus2, 20), "Failed to set periodic status frame 2 rate");
            
            // TODO:replace above with this:
            // checkNeoError(motor.setControlFramePeriodMs(100)), "Failed to set periodic status frame 0 rate");
            // checkNeoError(motor.setControlFramePeriodMs(20)), "Failed to set periodic status frame 1 rate");
            // checkNeoError(motor.setControlFramePeriodMs(20)), "Failed to set periodic status frame 2 rate"); 
            //Set neutral mode to brake

            System.out.println("----------------------- THIS IS A TEST ----------------------------------");


            // motor.setIdleMode(IdleMode.kBrake); TODO: brake to begin with

            // Setup encoder
            RelativeEncoder encoder = motor.getEncoder();
            double positionConversionFactor = Math.PI * moduleConfiguration.getWheelDiameter() * moduleConfiguration.getDriveReduction();
            // encoder.setPositionConversionFactor(positionConversionFactor);
            // encoder.setVelocityConversionFactor(positionConversionFactor / 60.0);TODO:modify conversions


            return new ControllerImplementation(motor, encoder);
        }
    }

    private static class ControllerImplementation implements DriveController {
        private final SparkMax motor;
        private final RelativeEncoder encoder;

        private ControllerImplementation(SparkMax motor, RelativeEncoder encoder) {
            this.motor = motor;
            this.encoder = encoder;
        }

        @Override
        public void setReferenceVoltage(double voltage) {
            motor.setVoltage(voltage);
            // motor.setVoltage(voltage);
        }

        @Override
        public double getPosition() {
            return encoder.getPosition();
        }

        @Override
        public double getStateVelocity() {
            return encoder.getVelocity();
        }

        @Override
        public void setCanStatusFramePeriodReductions() {
            // System.out.println("Start Neo Drive Can Reduction.");
            // motor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus0, 100);
            // motor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, 20);
            // motor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, 20);
            // System.out.printf("Drive Neo %1d: Reduced CAN message rates.", motor.getDeviceId());
            // System.out.println();
        }
    }
}
