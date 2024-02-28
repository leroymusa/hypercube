#include <Wire.h>
#include <Adafruit_BMP280.h>
#include <SPI.h>
// #include <SD.h>
#include <Adafruit_BNO08x.h>
#define BNO08X_RESET -1
// Pin definitions
const int buzzerPin = 17; const int beepDuration = 0;  const int beepDuration2 = 500; const int beepDuration3 = 3000;
int greenled = 8; int redled = 7; int whiteled = 4;

// BMP280 sensor object
Adafruit_BMP280 bmp;

// BNO085(x) sensor object
Adafruit_BNO08x  bno08x(BNO08X_RESET);
sh2_SensorValue_t sensorValue;
// SD card related variables
// const int chipSelect = 10;
// File dataFile;
unsigned long logInterval = 125; // Logging interval in milliseconds

//Acceleration module
float currentAltitude = 0.0; float previousAltitude = 0.0; float acceleration = 0.0;



// SD card related variables
//const unsigned long delayDuration1000 = 1000;  // Logging interval in milliseconds

// Variables for liftoff detection
#define LIFTOFF_THRESHOLD 5.0
float initialAltitude;
bool liftoffDetected = false;
int liftoffRecorded = 0; // Variable to record liftoff (0 for false, 1 for true)


bool isBeeping = false;          // Flag to indicate if the buzzer is active
bool createNewFile = false; // Flag to indicate if a new file needs to be created


void setup() {
  // Initialize Serial communication
  Serial.begin(9600);
  pinMode(buzzerPin, OUTPUT);   pinMode(redled, OUTPUT);  pinMode(whiteled, OUTPUT); pinMode(greenled, OUTPUT);    digitalWrite(redled, LOW);  digitalWrite(whiteled, LOW); digitalWrite(greenled, LOW);
  Wire.begin();// Initialize I2C communication

  // Initialize the BMP280 sensor
  if (!bmp.begin(0x77)) {
    /* Failed to initialize BMP280*/digitalWrite(redled, HIGH);;  // Halt the program
    Serial.println("BMP FAIL!");
    tone(buzzerPin, 200, 750);
    delay(1000);
    noTone(buzzerPin);
    delay(1000);
    tone(buzzerPin, 200, 750);
    delay(1000);
    noTone(buzzerPin);
    delay(1000);
    tone(buzzerPin, 200, 750);
    delay(1000);
    noTone(buzzerPin);
    delay(750);
    tone(buzzerPin, 200, 750);
    delay(1000);
    noTone(buzzerPin);
    while (1);
  }
  bmp.setSampling(Adafruit_BMP280::MODE_NORMAL,     /* Operating Mode. */
                  Adafruit_BMP280::SAMPLING_X2,     /* Temp. oversampling */
                  Adafruit_BMP280::SAMPLING_X16,    /* Pressure oversampling */
                  Adafruit_BMP280::FILTER_X16,      /* Filtering. */
                  Adafruit_BMP280::STANDBY_MS_125); /* Standby time. */

  // Initialize the SD card
  // if (!SD.begin(chipSelect)) {/* Failed to initialize SD card*/digitalWrite(redled, HIGH);Serial.println("SD CARD FAIL!");tone(buzzerPin, 200, 750); delay(1000);noTone(buzzerPin);delay(1000);tone(buzzerPin, 200, 750); delay(1000);noTone(buzzerPin); delay(1000);tone(buzzerPin, 200, 750); delay(1000);noTone(buzzerPin);delay(750);tone(buzzerPin, 200, 750); delay(1000);noTone(buzzerPin);   while (1);} // Halt the program



  /*
    // Check if a file already exists
    if (SD.exists("flightdata.txt")) {  /* Find a unique file name*/
  /*  for (uint8_t i = 0; i < 100; i++) { char fileName[15];  sprintf(fileName, "flightdata%d.txt", i);
      if (!SD.exists(fileName)) {  createNewFile = true;  break;}}}
         else {createNewFile = true;}
  */


  // Create a new file if required
  /*
    if (createNewFile) {
    dataFile = SD.open("flightdata.txt", FILE_WRITE);
    if (dataFile) { dataFile.println("Timestamp,Pressure,Temperature,Altitude,Event");  dataFile.close();}
       else {  /* Failed to create a new file*/
  /*    digitalWrite(redled, HIGH);while (1);tone(buzzerPin, 200, 750); delay(1000);noTone(buzzerPin);delay(1000);tone(buzzerPin, 200, 750); delay(1000);noTone(buzzerPin); delay(1000);tone(buzzerPin, 200, 750); delay(1000);noTone(buzzerPin);delay(750);tone(buzzerPin, 200, 750); delay(1000);noTone(buzzerPin);}} // Halt the program
  */
  /* Successful startup*/ //beepSuccess();

  // Calibrate attitude sensor on startup
  initialAltitude = bmp.readAltitude(1008.8); previousAltitude = initialAltitude;
  liftoffDetected = false;
  digitalWrite(greenled, HIGH);  digitalWrite(whiteled, LOW);  tone(buzzerPin, 2525, 750); delay(1000); noTone(buzzerPin); delay(1000); tone(buzzerPin, 2525, 750); delay(1000); noTone(buzzerPin); delay(1000); tone(buzzerPin, 2525, 750); delay(1000); noTone(buzzerPin); delay(750); tone(buzzerPin, 2525, 750); delay(1000); noTone(buzzerPin);
}

void loop() {

  static unsigned long previousMillis = 0;
  static unsigned long previousTime2 = 0;
  unsigned long currentMillis = millis();

  if (currentMillis - previousMillis >= logInterval) {







    // Read sensor data
    float pressure = bmp.readPressure() / 100.0; // Convert Pa to hPa
    float temperature = bmp.readTemperature();
    float currentAltitude = bmp.readAltitude(1008.8); // Adjust to sea level pressure


    // Calculate acceleration using the formula: Acceleration = (change in altitude) / (change in time)
    // Make sure to avoid division by zero if the time interval is very short (or zero).
    float deltaTime = (currentMillis - previousMillis) / logInterval; float deltaAltitude = currentAltitude - previousAltitude; acceleration = (deltaTime > 0) ? (deltaAltitude / deltaTime) : 0.0;

    float velocity = ((1 / .125) * (currentAltitude - previousAltitude));



    // Serial.print(acceleration); Serial.print(",");

    Serial.print(currentMillis); Serial.print(",");
    Serial.print(currentAltitude);  Serial.print(",");
    Serial.print(initialAltitude);  Serial.print(",");
    Serial.print(velocity); Serial.print(",");
    Serial.print(acceleration);  Serial.print(",");
    Serial.print(temperature);  Serial.print(",");
    Serial.print(pressure);  Serial.print(",");
    Serial.print(liftoffDetected);  Serial.println();


    if (!liftoffDetected && (currentAltitude - initialAltitude) >= LIFTOFF_THRESHOLD) {
      liftoffDetected = true; Serial.println("Liftoff detected!"); digitalWrite(whiteled, HIGH);
    }

    //"currentMillis,CurrentAltitude,velocity,acceleration,Temperature,pressure,Event"
    // Log sensor data and events to the SD card
    // dataFile = SD.open("flightdata.txt", FILE_WRITE);
    /*    if (dataFile) {
          String logEntry = String(currentMillis) + "," +
                            String(currentAltitude); + "," +
                            String(velocity); + "," +
                            String(acceleration) + "," +
                            String(temperature) + "," +
                            String(pressure); //+ "," +
                            String(liftoffDetected); //+ "," +
                            //String(acceleration) + "," +
                            //String(acceleration) + "," +
                            //detectEvent();
          dataFile.println(logEntry);
          dataFile.close();
        }
    */

    previousAltitude = currentAltitude;
    previousMillis = currentMillis;
  }

  if (liftoffDetected) {
    if (currentMillis - previousTime2 >= beepDuration) {
      tone(buzzerPin, 2500);
    }
    if (currentMillis - previousTime2 >= beepDuration2) {
      noTone(buzzerPin);
    }
    if (currentMillis - previousTime2 >= beepDuration3) {
      previousTime2 = currentMillis;
    }
  }






}






/*String detectEvent() {// prob delet
  // TODO: Implement event detection logic based on sensor data
  // Return the event name as a string (e.g., "Liftoff", "Apogee", "Touchdown")
  return "Event";
  }*/



//void beepError() {
//  for (int i = 0; i < 5; i++) { tone(buzzerPin, 200, 500);  /* Positive double beep*/   delay(delayDuration1000);Serial.println("ERRROR!");}}


//void beepSuccess() {
//  for (int i = 0; i < 5; i++) {   tone(buzzerPin, 2525, 500); /* Positive double beep*/   delay(delayDuration1000);Serial.println("SUCCESS!");  }}