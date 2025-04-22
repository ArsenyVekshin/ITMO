
// #include <SPI.h>
#include <Adafruit_BMP280.h>

#define BMP_SCK  (13)
#define BMP_MISO (12)
#define BMP_MOSI (11)
#define BMP_CS   (10)

Adafruit_BMP280 bmp(BMP_CS); // hardware SPI



#define SetBit(reg, bita) reg |= (1<<bita)

void serialSetup() {
  uint16_t baudRate = 38400;
  uint16_t ubrr = 16000000 / 16 / baudRate - 1;

  UBRR0H = (unsigned char) (ubrr >> 8);
  UBRR0L = (unsigned char) ubrr;

  SetBit(UCSR0B, TXEN0);
  SetBit(UCSR0B, RXEN0);
  SetBit(UCSR0B, RXCIE0);

  SetBit(UCSR0C, 1);
  SetBit(UCSR0C, 2);

}



void setup() {
  serialSetup();

  unsigned status;
  status = bmp.begin(BMP280_ADDRESS_ALT, BMP280_CHIPID);
  status = bmp.begin();

  /* Default settings from datasheet. */
  bmp.setSampling(Adafruit_BMP280::MODE_NORMAL,     /* Operating Mode. */
                  Adafruit_BMP280::SAMPLING_X2,     /* Temp. oversampling */
                  Adafruit_BMP280::SAMPLING_X16,    /* Pressure oversampling */
                  Adafruit_BMP280::FILTER_X16,      /* Filtering. */
                  Adafruit_BMP280::STANDBY_MS_500); /* Standby time. */
}

unsigned char serialCheckTx(){
    return(UCSR0A & (1<<UDRE0));
}

void serial_send(unsigned char DataOut){
    while(serialCheckTx() == 0){;}
    UDR0 = DataOut;
}

uint8_t crc8(String data) {
    uint8_t crc = 0x00;
    const uint8_t polynomial = 0x07;
    for (size_t i = 0; i < data.length(); i++) {
        crc ^= data[i]; 
        for (uint8_t j = 0; j < 8; j++) {
            if (crc & 0x80) {
                crc = (crc << 1) ^ polynomial;
            } else {
                crc <<= 1;
            }
        }
    }
    return crc;
}

#define SYNC_BYTE 0x5A

void serial_send_packet(String data) {
  if (data.length() > 0xFF) return;

  serial_send(SYNC_BYTE);
  serial_send(data.length());
  for (size_t i = 0; i < data.length(); i++) {
    serial_send(data[i]);
  }
  serial_send(crc8(data));
}

String dataToString(uint8_t* data, uint8_t length) {
  String result = "";
  for (uint8_t i = 0; i < length; i++) {
    result += (char)data[i];
  }
  return result;
}

void loop() {
    serial_send_packet(F("Temperature = "));
    serial_send_packet(String(bmp.readTemperature()));
    serial_send_packet(" *C\n");
    serial_send_packet(F("Pressure = "));
    serial_send_packet(String(bmp.readPressure()));
    serial_send_packet(" Pa\n");

    delay(1000);
}
