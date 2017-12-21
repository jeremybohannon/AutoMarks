from navigation import MechWheelController
from time import sleep

drive = MechWheelController()

print("Starting Robot")

# Move forward 5 secs
drive.driveStraight(255)
sleep(5)
# Move Reverse 5 secs
drive.driveStraight(-255)
sleep(5)
# Strafe Left 5 secs
drive.strafe(255)
sleep(5)
# Strafe Right 5 secs
drive.strafe(-255)
sleep(5)
# Rotate left 5 secs
drive.rotate(255)
sleep(5)
# Rotate right 5 secs
drive.rotate(-255)
sleep(5)
# move -45 forward 5 secs
drive.neg45Movement(255)
sleep(5)
# move -45 reverse 5 secs
drive.neg45Movement(-255)
sleep(5)
# mvoe +45 forward 5 secs
drive.pos45Movement(255)
sleep(5)
# move +45 reverse 5 secs
drive.pos45Movement(-255)
sleep(5)

print("Robot Finished")



