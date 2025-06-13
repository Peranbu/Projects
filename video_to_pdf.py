# Step 1: Install required libraries
!pip install opencv-python fpdf

# Step 2: Import libraries
import cv2
from fpdf import FPDF
import os
from google.colab import files

# Step 3: Define function
def video_to_pdf_custom_timestamps(video_path, output_pdf_path, interval_seconds=90):
    cap = cv2.VideoCapture(video_path)
    if not cap.isOpened():
        print("Error opening video file.")
        return

    fps = cap.get(cv2.CAP_PROP_FPS)
    total_frames = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
    duration_seconds = total_frames / fps

    pdf = FPDF()
    timestamp = 30  # Start from 30 seconds

    while timestamp < duration_seconds:
        frame_number = int(timestamp * fps)
        cap.set(cv2.CAP_PROP_POS_FRAMES, frame_number)
        ret, frame = cap.read()
        if not ret:
            break

        # Save frame as image
        image_path = f"/content/frame_{timestamp}.jpg"
        cv2.imwrite(image_path, frame)

        # Add to PDF
        pdf.add_page()
        pdf.image(image_path, x=10, y=10, w=190)
        os.remove(image_path)

        timestamp += interval_seconds  # Move to next 90s (1 min 30s)

    cap.release()
    pdf.output(output_pdf_path)
    print("PDF created successfully.")

# Step 4: Copy video from Drive to Colab (Replace path with your video location)
from google.colab import drive
drive.mount('/content/drive')
!cp "/content/drive/MyDrive/Videos/videoplayback.mp4" "/content/videoplayback.mp4"

# Step 5: Convert to PDF using timestamps 0:30, 1:30, 2:30, etc.
video_to_pdf_custom_timestamps("/content/videoplayback.mp4", "/content/output.pdf")

# Step 6: Download the PDF
files.download("/content/output.pdf")
