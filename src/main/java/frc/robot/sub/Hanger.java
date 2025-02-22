package frc.robot.sub;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ConstantsHanger;

public class Hanger extends SubsystemBase{

    private final SparkMax RightHanger, LeftHanger;
    private SparkMaxConfig leftConfig, rightConfig;

    public Hanger(){

        RightHanger = new SparkMax(ConstantsHanger.RightHangerPort, MotorType.kBrushless);
        LeftHanger = new SparkMax(ConstantsHanger.LeftHangerPort, MotorType.kBrushless);


        rightConfig = new SparkMaxConfig();
        leftConfig = new SparkMaxConfig();

        burnFlash();
    
    }

    private void burnFlash(){

        RightHanger.setCANTimeout(250);
        rightConfig.idleMode(IdleMode.kCoast).inverted(ConstantsHanger.r_inverted);
        RightHanger.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        RightHanger.setCANTimeout(0);

        LeftHanger.setCANTimeout(250);
        leftConfig.idleMode(IdleMode.kCoast).inverted(ConstantsHanger.l_inverted);
        LeftHanger.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        LeftHanger.setCANTimeout(0);

    }

    public void setSpeed(double speed){
        RightHanger.set(speed);
        LeftHanger.set(speed);
    }

    public void stop(){
        RightHanger.stopMotor();
        LeftHanger.stopMotor();
    }
}
