package com.techup.minor_cineplex.validation;

import jakarta.validation.GroupSequence;

@GroupSequence({FirstGroup.class, SecondGroup.class})
public interface ValidationOrder {}