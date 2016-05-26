package io.github.scamandrill.models

/**
  * The metadata
  *
  * @param name          - a unique identifier for the metadata field
  * @param view_template - optional Mustache template to control how the metadata is rendered in your activity log
  */
case class MMeteadatapAdd(name: String,
                          view_template: String) extends MandrillRequest

/**
  * The metadata
  *
  * @param name - the unique identifier of the metadata field to update
  */
case class MMeteadatapDelete(name: String) extends MandrillRequest
