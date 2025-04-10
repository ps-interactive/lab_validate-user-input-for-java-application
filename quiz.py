import json
import random
import os 

def load_questions(filepath):
  if not os.path.exists(filepath):
      print(f"Error: File not found at '{filepath}'")
      return None
  try:
    with open(filepath, 'r', encoding='utf-8') as f:
      questions = json.load(f)
    if not isinstance(questions, list):
        print(f"Error: JSON file should contain a list of questions.")
        return None
    for i, q in enumerate(questions):
        if not all(key in q for key in ["question", "options", "answer"]):
             print(f"Error: Question {i+1} is missing required keys ('question', 'options', 'answer').")
             return None
        if not isinstance(q["options"], dict):
            print(f"Error: 'options' for question {i+1} should be a dictionary (e.g., {{'A': 'text', ...}}).")
            return None
        if q["answer"] not in q["options"]:
             print(f"Error: The 'answer' for question {i+1} ('{q['answer']}') is not a valid key in its 'options'.")
             return None
    return questions
  except json.JSONDecodeError:
    print(f"Error: Could not decode JSON from file '{filepath}'. Check the file format.")
    return None
  except Exception as e:
    print(f"An unexpected error occurred while loading the file: {e}")
    return None

def run_quiz(questions):
  if not questions:
    print("No questions loaded to run the quiz.")
    return
  num_questions_to_ask = 10
  shuffle_questions = True
  if shuffle_questions:
    random.shuffle(questions)
  if num_questions_to_ask is not None:
      actual_questions = questions[:min(num_questions_to_ask, len(questions))]
  else:
      actual_questions = questions # Ask all
  if not actual_questions:
      print("No questions available to ask.")
      return
  score = 0
  total_asked = len(actual_questions)
  print("\n--- Starting the Quiz ---")
  print(f"You will be asked {total_asked} question(s).\n")
  for i, q in enumerate(actual_questions):
    print(f"Question {i + 1}/{total_asked}: {q['question']}")
    valid_options = []
    for key, value in q['options'].items():
      print(f"  {key}) {value}")
      valid_options.append(key.upper()) # Store valid option keys (uppercase)
    while True:
      user_answer = input("Your answer: ").strip().upper()
      if user_answer in valid_options:
        break
      else:
        print(f"Invalid option. Please enter one of: {', '.join(valid_options)}")
    correct_answer = q['answer'].upper()
    if user_answer == correct_answer:
      print("Correct!\n")
      score += 1
    else:
      print(f"Incorrect. The correct answer was: {correct_answer}) {q['options'][q['answer']]}\n")
  print("--- Quiz Finished ---")
  print(f"Your final score: {score} out of {total_asked}")
  try:
      percentage = (score / total_asked) * 100
      print(f"Percentage: {percentage:.2f}%")
  except ZeroDivisionError:
       print("Percentage: N/A (No questions asked)")
def main():
  json_filepath = "/home/ubuntu/lab/.mcq/mcq.json"
  questions = load_questions(json_filepath)
  if questions:
    run_quiz(questions)
if __name__ == "__main__":
  main()
