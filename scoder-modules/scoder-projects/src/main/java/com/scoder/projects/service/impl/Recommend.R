library(jsonlite) # For JSON parsing and output
library(fmsb)     # For radar chart generation

# Combine all command-line arguments into a single JSON string
args <- commandArgs(trailingOnly = TRUE)
json_input <- paste(args, collapse = " ")

# Parse JSON input as a list
matchData <- fromJSON(json_input, simplifyVector = FALSE)

# Extract user-specific data
user <- list(
  Skills = matchData[[1]]$userSkills,      # User skills
  Experience = matchData[[1]]$userExperience, # User experience
  Goals = matchData[[1]]$userGoals        # User goals
)

# Function to calculate compatibility scores
calculate_match_score <- function(user, team) {
  # Calculate skill match
  skill_match <- length(intersect(user$Skills, team$Skills)) / length(team$Skills) * 100
  # Calculate experience match
  experience_match <- ifelse(!is.na(team$Experience) && user$Experience >= team$Experience, 100, 0)

  # Calculate goals match
  goals_match <- ifelse(!is.na(team$Goals) && user$Goals >= team$Goals, 100, 0)

  # Compute total weighted score
  total_score <- (skill_match * 0.5) +
    (experience_match * 0.3) +
    (goals_match * 0.2)

  return(list(
    SkillMatch = skill_match,
    ExperienceMatch = experience_match,
    GoalsMatch = goals_match,
    TotalScore = total_score
  ))
}

# Compute match scores for all teams
results <- lapply(matchData, function(team) {
  score <- calculate_match_score(user, team) # Calculate scores for each team
  team$SkillMatch <- score$SkillMatch       # Add skill match score
  team$ExperienceMatch <- score$ExperienceMatch # Add experience match score
  team$GoalsMatch <- score$GoalsMatch       # Add goals match score
  team$MatchScore <- score$TotalScore       # Add total score
  return(team)
})

# Sort teams by match score in descending order
results <- results[order(sapply(results, function(team) team$MatchScore), decreasing = TRUE)]

# Directory to store radar charts
output_dir <- "images"
dir.create(output_dir, showWarnings = FALSE)

# Generate radar charts for each team
for (i in seq_along(results)) {
  team <- results[[i]]

  radar_data <- data.frame(
    SkillMatch = team$SkillMatch,
    ExperienceMatch = team$ExperienceMatch,
    GoalsMatch = team$GoalsMatch
  )

  radar_data <- rbind(
    rep(100, 3),
    rep(0, 3),
    radar_data
  )

  output_file <- file.path(
    output_dir,
    paste0(
      "radar_chart_",
      gsub(" ", "_", team$Team),
      "_", matchData[[1]]$userId[[1]],
      ".png"
    )
  )

  # Set output size with larger dimensions
  png(output_file, width = 500, height = 500)

  # Adjust margins for more space
  par(mar = c(1, 1, 1, 1))  # Add more margin space

  # Define axis labels dynamically with line breaks
  skill_value <- round(radar_data[3, "SkillMatch"], 1)
  experience_value <- round(radar_data[3, "ExperienceMatch"], 1)
  goals_value <- round(radar_data[3, "GoalsMatch"], 1)
  axis_labels <- c(
    paste0("SkillMatch\n", skill_value, "%"),
    paste0("ExperienceMatch\n", experience_value, "%"),
    paste0("GoalsMatch\n", goals_value, "%")
  )

  # Generate radar chart
  radarchart(
    radar_data,
    axistype = 1,
    pcol = c("blue"),
    pfcol = c(rgb(0.2, 0.5, 0.5, 0.5)),
    plwd = 2,
    cglcol = "grey",
    cglty = 1,
    caxislabels = seq(0, 100, 25),  # Axis scale
    axislabcol = "black",
    vlabels = axis_labels  # Use dynamic labels with line breaks
  )

  dev.off()
  results[[i]]$RadarImg <- as.character(output_file)
}

# Simplify results
simplified_results <- lapply(results, function(team) {
  RadarImg <- if (!is.null(team$RadarImg) && team$RadarImg != "") {
    as.character(team$RadarImg)
  } else {
    NULL # 使用 NULL 表示字段为空
  }
  list(
    Team = team$Team,
    SkillMatch = as.numeric(team$SkillMatch),
    ExperienceMatch = as.numeric(team$ExperienceMatch),
    GoalsMatch = as.numeric(team$GoalsMatch),
    MatchScore = as.numeric(team$MatchScore),
    RadarImg = RadarImg
  )
})

# Output JSON
cat(toJSON(simplified_results, pretty = TRUE, auto_unbox = TRUE))