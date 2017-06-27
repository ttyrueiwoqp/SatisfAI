CREATE TABLE groupon_tickets_metric_5 (
  tickets_id BIGINT,
  metric_sets_agent_wait_time_in_minutes_business INT,
  metric_sets_agent_wait_time_in_minutes_calendar INT,
  metric_sets_assigned_at TIMESTAMP,
  metric_sets_assignee_stations INT,
  metric_sets_assignee_updated_at TIMESTAMP,
  PRIMARY KEY (tickets_id)
);
CREATE TABLE groupon_tickets_metric_10 (
  tickets_id BIGINT,
  metric_sets_created_at TIMESTAMP,
  metric_sets_first_resolution_time_in_minutes_business INT,
  metric_sets_first_resolution_time_in_minutes_calendar INT,
  metric_sets_full_resolution_time_in_minutes_business INT,
  metric_sets_full_resolution_time_in_minutes_calendar INT,
  PRIMARY KEY (tickets_id)
);
CREATE TABLE groupon_tickets_metric_15 (
  tickets_id BIGINT,
  metric_sets_group_stations INT,
  metric_sets_initially_assigned_at TIMESTAMP,
  metric_sets_latest_comment_added_at TIMESTAMP,
  metric_sets_on_hold_time_in_minutes_business INT,
  metric_sets_on_hold_time_in_minutes_calendar INT,
  PRIMARY KEY (tickets_id)
);
CREATE TABLE groupon_tickets_metric_20 (
  tickets_id BIGINT,
  metric_sets_reopens INT,
  metric_sets_replies INT,
  metric_sets_reply_time_in_minutes_business INT,
  metric_sets_reply_time_in_minutes_calendar INT,
  metric_sets_requester_updated_at TIMESTAMP,
  PRIMARY KEY (tickets_id)
);
CREATE TABLE groupon_tickets_metric_25 (
  tickets_id BIGINT,
  metric_sets_requester_wait_time_in_minutes_business INT,
  metric_sets_requester_wait_time_in_minutes_calendar INT,
  metric_sets_solved_at TIMESTAMP,
  metric_sets_status_updated_at TIMESTAMP,
  metric_sets_updated_at TIMESTAMP,
  PRIMARY KEY (tickets_id)
);

