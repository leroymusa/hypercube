import rclpy
from rclpy.node import Node
from interfaces.msg import Completed  # Import the custom message
from sensor_msgs.msg import NavSatFix
from math import radians, cos, sin, asin, atan2, sqrt, degrees

Radius = 6365.766

def Haversine(lat1, lon1, lat2, lon2):
    # Convert latitude and longitude from degrees to radians
    lat1 = radians(lat1)
    lon1 = radians(lon1)
    lat2 = radians(lat2)
    lon2 = radians(lon2)

    # Calculate the differences in latitude and longitude
    delta_lat = lat2 - lat1
    delta_lon = lon2 - lon1

    # Haversine formula
    distance = 2 * Radius * asin(sqrt(
        sin(delta_lat / 2) ** 2 +
        cos(lat1) * cos(lat2) * sin(delta_lon / 2) ** 2
    ))
    return distance

class GpsDistanceNode(Node):
    def __init__(self):
        super().__init__('gps_distance_node')
        self.subscription = self.create_subscription(
            NavSatFix,
            'GCS',
            self.gps_callback,
            10)
        self.publisher = self.create_publisher(
            Completed,  # Use the custom message
            'Dish',
            10)
        
    def gps_callback(self, msg):
        # Access the latitude and longitude data from the NavSatFix message
        latitude = msg.latitude
        longitude = msg.longitude

        # Calculate distance using the Haversine formula
        distance = Haversine(latitude, longitude, 51.42287924341543, -112.64106837507106)

        # Calculate heading using the provided function
        heading = get_heading(longitude, latitude)

        # Publish the results in the custom message
        completed_msg = Completed()
        completed_msg.latitude = latitude
        completed_msg.longitude = longitude
        completed_msg.distance = round(distance * 1000, 1)  # Convert to meters and round to one decimal point
        completed_msg.heading = round(heading, 1)  # Round heading to one decimal point
        self.publisher.publish(completed_msg)

def get_heading(longitude, latitude):
    lat1 = radians(51.42287924341543)  # Fixed dish latitude
    lon1 = radians(-112.64106837507106)  # Fixed dish longitude
    lat2 = radians(latitude)
    lon2 = radians(longitude)

    delta_lat = lat2 - lat1
    delta_lon = lon2 - lon1

    y = sin(delta_lon) * cos(lat2)
    x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(delta_lon)
    bearing = atan2(y, x)
    bearing = degrees(bearing)
    bearing = (bearing + 360) % 360
    heading = round(bearing, 1)
    return heading

def main(args=None):
    rclpy.init(args=args)
    gps_distance_node = GpsDistanceNode()
    rclpy.spin(gps_distance_node)
    gps_distance_node.destroy_node()
    rclpy.shutdown()

if __name__ == '__main__':
    main()
    